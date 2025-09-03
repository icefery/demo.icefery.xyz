package limit_util

import (
	"log"
	"sync"
	"time"

	"golang.org/x/time/rate"
)

type Config struct {
	Key      string        // 名称
	Limit    int           // 限制调用次数(每分钟)
	Duration time.Duration // 限制调用时长
}

type RateLimiter[T any] struct {
	configs  map[string]Config        // map[configKey, config]
	limiters map[string]*rate.Limiter // map[limiterKey, limiter]
	untils   map[string]time.Time     // map[limiterKey, time]
	buffers  map[string][]T           // map[limiterKey, input]
	mutex    sync.Mutex
}

func NewRateLimiter[T any](configs []Config) *RateLimiter[T] {
	configMap := make(map[string]Config)
	for _, config := range configs {
		configMap[config.Key] = config
	}
	return &RateLimiter[T]{
		configs:  configMap,
		limiters: make(map[string]*rate.Limiter),
		untils:   make(map[string]time.Time),
		buffers:  make(map[string][]T),
	}
}

func (this *RateLimiter[T]) LimitCall(configKey string, targetKey string, fnInput T, fn func(string, string, []T)) {
	this.mutex.Lock()
	defer this.mutex.Unlock()

	if _, ok := this.configs[configKey]; !ok {
		fn(configKey, targetKey, []T{fnInput})
		return
	}

	limiterKey := configKey + ":" + targetKey
	if _, ok := this.limiters[limiterKey]; !ok {
		config := this.configs[configKey]
		this.limiters[limiterKey] = rate.NewLimiter(rate.Every(time.Minute/time.Duration(config.Limit)), config.Limit)
	}

	// 1. 已经被限制调用
	if until, ok := this.untils[limiterKey]; ok && time.Now().Before(until) {
		this.buffers[limiterKey] = append(this.buffers[limiterKey], fnInput)
		log.Printf("[已经被限制调用] limiterKey=%v until=%v bufferSize=%v \n", limiterKey, until.Format(time.DateTime), len(this.buffers[limiterKey]))
		return
	}

	// 2. 开始被限制调用
	limiter := this.limiters[limiterKey]
	if !limiter.Allow() {
		this.untils[limiterKey] = time.Now().Add(this.configs[configKey].Duration)
		this.buffers[limiterKey] = []T{fnInput}
		log.Printf("[开始被限制调用] limiterKey=%v duration=%v bufferSize=%v \n", limiterKey, this.configs[configKey].Duration, len(this.buffers[limiterKey]))
		go func(limiterKey string) {
			// 3. 结束被限制调用
			time.Sleep(this.configs[configKey].Duration)
			this.mutex.Lock()
			defer this.mutex.Unlock()
			if len(this.buffers[limiterKey]) > 0 {
				log.Printf("[结束被限制调用] limiterKey=%v buffer=%v \n", limiterKey, len(this.buffers[limiterKey]))
				fn(configKey, targetKey, this.buffers[limiterKey])
				delete(this.buffers, limiterKey)
				delete(this.untils, limiterKey)
			}
		}(limiterKey)
		return
	}
}
