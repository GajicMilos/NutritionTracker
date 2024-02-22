package raf.edu.rs.nutritiontracker.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import raf.edu.rs.nutritiontracker.R
import raf.edu.rs.nutritiontracker.models.domain.Category
import raf.edu.rs.nutritiontracker.models.domain.Filter

class CategoriesAdapter(private val mList: List<Category>?) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private lateinit var parent:ViewGroup;
    private var itemClickListener: OnFilterItemClickListener? = null
    fun setOnItemClickListener(listener: OnFilterItemClickListener) {
        itemClickListener = listener
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        parent.findViewById<Button>(R.id.editButtonDP)
      this.parent=parent;
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = mList?.get(position)

        println(position)
        // sets the image to the imageview from our itemHolder class
        if (category != null) {


            Glide.with(parent.context).load(category.strCategoryThumb).into(holder.imageView);
            holder.textView.text = category.strCategory
            val dialogView = LayoutInflater.from(parent.context).inflate(R.layout.category_desc_dialog, null)
            val dialog = AlertDialog.Builder(parent.context)
                .setView(dialogView)
                .create()


            holder.itemView.setOnClickListener {
                println(category.strCategory)
                itemClickListener?.onItemClick(Filter.CATEGORY, category.strCategory!!)
            }







            holder.button?.setOnClickListener {
                dialog.show()
                println(category.strCategory)
                val tv = dialog.findViewById<TextView>(R.id.textView5)
                val buttonClose = dialog.findViewById<Button>(R.id.buttonClose)

                buttonClose?.setOnClickListener {
                    dialog.dismiss()
                }

                tv?.text = category.strCategoryDescription

            }
          //  dialog.show()
        }

        // sets the text to the textview from our itemHolder class


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
        val button: ImageButton=itemView.findViewById(R.id.editButtonDP)
    }

}

