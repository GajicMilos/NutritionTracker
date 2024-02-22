package raf.edu.rs.nutritiontracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.models.domain.Filter
import raf.edu.rs.nutritiontracker.models.domain.Ingredient

class IngredientsAdapter(private val mList: List<Ingredient>?) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private lateinit var parent: ViewGroup;
    private var itemClickListener: OnFilterItemClickListener? = null
    fun setOnItemClickListener(listener: OnFilterItemClickListener) {
        itemClickListener = listener
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        this.parent = parent;
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ingredient = mList?.get(position)
        if (ingredient != null) {
            holder.textView.text = ingredient.strIngredient
            Glide.with(parent.context).
            load("https://www.themealdb.com/images/ingredients/"+ingredient.strIngredient+".png").
            into(holder.imageView);
            holder.itemView.setOnClickListener {

                itemClickListener?.onItemClick(Filter.INGREDIENT, ingredient.strIngredient)
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
        val imageView: ImageView= itemView.findViewById(R.id.ivIngredient)
    }
}

