package raf.edu.rs.nutritiontracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.models.domain.Area
import raf.edu.rs.nutritiontracker.models.domain.Filter

class AreasAdapter(private val mList: List<Area>?) : RecyclerView.Adapter<AreasAdapter.ViewHolder>() {

    private lateinit var parent: ViewGroup;
    private var itemClickListener:OnFilterItemClickListener? = null
    fun setOnItemClickListener(listener:OnFilterItemClickListener) {
        itemClickListener = listener
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item

        this.parent = parent;
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val area = mList?.get(position)

        if (area != null) {
            holder.textView.text = area.strArea
            holder.imageView.isVisible=false
            holder.itemView.setOnClickListener {

                itemClickListener?.onItemClick(Filter.AREA, area.strArea)
            }
        }

        // sets the text to the textview from our itemHolder class


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        if (mList != null)
            return mList.size
        return 0;
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.eventName)
        val imageView:ImageView=itemView.findViewById(R.id.ivIngredient)
    }
}

