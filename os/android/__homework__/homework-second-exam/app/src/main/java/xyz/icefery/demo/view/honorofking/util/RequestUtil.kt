package xyz.icefery.demo.view.honorofking.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import com.alibaba.fastjson.JSONObject
import okhttp3.OkHttpClient
import okhttp3.Request
import xyz.icefery.demo.view.honorofking.Hero
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

object RequestUtil {
    @JvmStatic
    fun requestHeroListAsync(withInfo: Boolean, handler: Handler) {
        Thread({
            var heroList: List<Hero> = ArrayList()
            try {
                run {
                    // list { name, hero_id, cover, type }
                    val request: Request = Request.Builder().url(getHeroListApiUrl()).build()
                    val response = OkHttpClient().newCall(request).execute()
                    val body = response.body
                    if (response.code == 200 && body != null) {
                        val rootNode = JSONObject.parseObject(body.string())
                        val listNode = rootNode.getJSONArray("list")
                        heroList = listNode.toJavaList(Hero::class.java)
                            .stream()
                            .peek { hero -> hero.coverBitmap = requestImage(hero.cover) }
                            .collect(Collectors.toList())
                    }
                }
                if (withInfo) {
                    run({
                        // info {...item, title, half_img, background_story, skill_list }
                        heroList = heroList.stream().peek({ hero ->
                            try {
                                val request: Request = Request.Builder().url(getHeroInfoApiUrl(hero.heroID)).build()
                                val response = OkHttpClient().newCall(request).execute()
                                val body = response.body
                                if (response.code == 200 && body != null) {
                                    val rootNode = JSONObject.parseObject(body.string())
                                    val infoNode = rootNode.getJSONObject("info")
                                    val heroInfo = infoNode.toJavaObject(Hero::class.java)
                                    hero.title = heroInfo.title
                                    hero.halfImg = heroInfo.halfImg
                                    hero.backgroundStory = heroInfo.backgroundStory
                                    hero.skillList = heroInfo.skillList
                                    hero.halfImgBitmap = requestImage(heroInfo.halfImg)
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }).collect(Collectors.toList())
                    })
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                val msg = Message.obtain()
                msg.obj = heroList
                handler.sendMessage(msg)
            }
        }).start()
    }

    @Throws(IOException::class)
    private fun requestImage(url: String?): Bitmap? {
        if (url == null) {
            return null
        }
        val request: Request = Request.Builder().url(url).build()
        val response = OkHttpClient().newCall(request).execute()
        val body = response.body
        if (response.code == 200 && body != null) {
            return BitmapFactory.decodeStream(body.byteStream())
        } else {
            return null
        }
    }

    fun requestImageAsync(url: String?, handler: Handler) {
        Thread({
            val msg = Message.obtain()
            try {
                msg.obj = requestImage(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            handler.sendMessage(msg)
        }).start()
    }

    @JvmStatic
    private fun getHeroListApiUrl(): String {
        return "http://gamehelper.gm825.com/wzry/hero/list"
    }

    @JvmStatic
    private fun getHeroInfoApiUrl(itemId: String?): String {
        return "http://gamehelper.gm825.com/wzry/hero/detail?hero_id=${itemId}"
    }
}