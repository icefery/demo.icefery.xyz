package main

import (
	"net/http"
	"strconv"
	"time"

	"call-limit-demo/src/utils/limit_util"

	"github.com/gin-gonic/gin"
)

func CallThirdParty(configKey string, targetKey string, inputs []int) {
	// fmt.Printf("[call thirdparty] configKey=%s targetKey=%s inputs=%v\n", configKey, targetKey, inputs)
}

// for i in {1..15}; do curl "http://127.0.0.1:8080/thirdparty/call?configKey=b&targetKey=$(whoami)&input=${i}"; done
// for i in {1..15}; do curl "http://127.0.0.1:8080/thirdparty/call-limit?configKey=b&targetKey=$(whoami)&input=${i}"; done
func main() {
	rl := limit_util.NewRateLimiter[int]([]limit_util.Config{
		{Key: "a", Limit: 100, Duration: 5 * time.Minute},
		{Key: "b", Limit: 10, Duration: 2 * time.Minute},
		{Key: "c", Limit: 5, Duration: 1 * time.Minute},
	})

	r := gin.Default()

	r.
		GET("/thirdparty/call", func(ctx *gin.Context) {
			configKey := ctx.Query("configKey")
			targetKey := ctx.Query("targetKey")
			input, _ := strconv.Atoi(ctx.Query("input"))
			CallThirdParty(configKey, targetKey, []int{input})
			ctx.JSON(http.StatusOK, gin.H{})
		}).
		GET("/thirdparty/call-limit", func(ctx *gin.Context) {
			configKey := ctx.Query("configKey")
			targetKey := ctx.Query("targetKey")
			input, _ := strconv.Atoi(ctx.Query("input"))
			rl.LimitCall(configKey, targetKey, input, CallThirdParty)
			ctx.JSON(http.StatusOK, gin.H{})
		})

	_ = r.Run(":8080")
}
