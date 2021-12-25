package xyz.icefery.demo.view.honorofking

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import xyz.icefery.demo.databinding.FragmentHonorOfKingBinding
import xyz.icefery.demo.view.honorofking.util.RequestUtil

import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class HonorOfKingFragment : Fragment() {
    companion object {
        @JvmField
        val TYPE_MAP = object : LinkedHashMap<String?, String?>() {
            init {
                put("1", "战士")
                put("2", "法师")
                put("3", "坦克")
                put("4", "刺客")
                put("5", "射手")
                put("6", "辅助")
            }
        }
    }

    private lateinit var binding: FragmentHonorOfKingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHonorOfKingBinding.inflate(inflater, container, false)

        // ViewPager
        val adapter = Adapter(this, ArrayList<Hero>())
        binding.viewPager.adapter = adapter

        // TabLayout
        TabLayoutMediator(binding.tabLayout, binding.viewPager, { tab, position ->
            tab.text = TYPE_MAP.values.toTypedArray()[position]
        }).attach()

        // Request data
        Toast.makeText(this.context, "Loading...", Toast.LENGTH_SHORT).show()
        RequestUtil.requestHeroListAsync(false, Handler(Looper.myLooper()!!, { msg: Message ->
            val list = msg.obj as ArrayList<Hero>
            adapter.heroList.addAll(list)
            adapter.notifyDataSetChanged()
            // Notify all list view adapter
            HeroListFragment.notifyDataChanged()
            Toast.makeText(context, "Waiting...", Toast.LENGTH_SHORT).show()
            true
        }))

        return binding.root
    }


    class Adapter(fragment: Fragment, val heroList: MutableList<Hero>) : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            val dataList = heroList.stream().filter({ hero ->
                hero.type!!.contains(TYPE_MAP.keys.toTypedArray()[position])
            }).collect(Collectors.toList())
            return HeroListFragment(dataList)
        }

        override fun getItemCount(): Int {
            return TYPE_MAP.size
        }
    }
}