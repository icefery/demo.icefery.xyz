package xyz.icefery.demo.view.honorofking

import android.graphics.Bitmap

class Hero {
    var name: String? = ""
    var title: String? = ""
    var cover: String? = ""
    var coverBitmap: Bitmap? = null
    var halfImg: String? = ""
    var halfImgBitmap: Bitmap? = null
    var type: List<String>? = null
    var backgroundStory: String? = ""
    var heroID: String? = ""
    var skillList: List<SkillList>? = null

    class SkillList {
        var name: String? = ""
        var icon: String? = ""
        var description: String? = ""
        var intro: String? = ""
        var tags: String? = ""
        var cd: String? = ""
        var manaCost: String? = ""

        override fun toString(): String {
            return "SkillList(name=$name, icon=$icon, description=$description, intro=$intro, tags=$tags, cd=$cd, manaCost=$manaCost)"
        }
    }

    override fun toString(): String {
        return "Hero(name=$name, title=$title, cover=$cover, coverBitmap=$coverBitmap, halfImg=$halfImg, halfImgBitmap=$halfImgBitmap, type=$type, backgroundStory=$backgroundStory, heroID=$heroID, skillList=$skillList)"
    }
}
