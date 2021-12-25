package xyz.icefery.demo.view.honorofking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.icefery.demo.R
import xyz.icefery.demo.databinding.FragmentHeroListBinding
import java.util.*
import java.util.function.Consumer

class HeroListFragment(private val dataList: List<Hero>) : Fragment() {
    private lateinit var binding: FragmentHeroListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHeroListBinding.inflate(inflater, container, false)
        binding.heroRecycleView.layoutManager = GridLayoutManager(context, 4)

        val adapter = Adapter(dataList)
        binding.heroRecycleView.adapter = adapter
        ADAPTER_LIST.add(adapter)

        return binding.root
    }

    // Static
    companion object {
        private val ADAPTER_LIST: MutableList<Adapter> = ArrayList()

        @JvmStatic
        fun notifyDataChanged() {
            ADAPTER_LIST.forEach(Consumer({ listAdapter -> listAdapter.notifyDataSetChanged() }))
        }
    }

    // Adapter
    class Adapter(private val dataList: List<Hero>) : RecyclerView.Adapter<VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hero_item, parent, false)
            return VH(itemView)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = dataList[position]
            holder.heroItemImageView.setImageBitmap(item.coverBitmap)
            holder.heroItemTextView.text = item.name
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }

    // ViewHolder
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val heroItemImageView: ImageView = itemView.findViewById(R.id.hero_item_image_view)
        val heroItemTextView: TextView = itemView.findViewById(R.id.hero_item_text_view)
    }
}