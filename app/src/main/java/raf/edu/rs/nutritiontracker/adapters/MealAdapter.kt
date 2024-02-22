package raf.edu.rs.nutritiontracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.models.domain.Meal


class MealAdapter(private val mList: List<Meal>?) : RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    var isMeal: Boolean =true;
    private lateinit var parent: ViewGroup;
    private var itemClickListener:OnMealItemClickListener? = null
    fun setOnItemClickListener(listener: OnMealItemClickListener) {
        itemClickListener = listener
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item

        this.parent=parent;
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meal_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val meal = mList?.get(position)

        if (meal != null) {
            Glide.with(parent.context).load(meal.strMealThumb).into(holder.imageView);
            holder.textView.text = meal.strMeal
            holder.itemView.setOnClickListener {
                itemClickListener?.onItemClickListener(meal, isMeal)
            }
        }

    }






    // return the number of the items in the list
    override fun getItemCount(): Int {
        if (mList!=null)
            return mList.size
        return 0;
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.eventName)

    }
}

