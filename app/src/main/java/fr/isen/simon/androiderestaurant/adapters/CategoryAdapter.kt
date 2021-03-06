package fr.isen.simon.androiderestaurant.adapters

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.simon.androiderestaurant.databinding.ItemPlatBinding
import fr.isen.simon.androiderestaurant.models.Plat
import fr.isen.simon.androiderestaurant.viewmodels.BasketViewModel

class CategoryAdapter(
    private val mPlats: List<Plat>,
    private val categoryClickListener: (Plat) -> Unit,
    private val categoryLongClickListener: (ImageButton) -> Unit,
    private val deletePlatAction: (Plat) -> Unit
): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemPlatBinding.inflate(inflater, parent, false)

        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val myItem = mPlats[position]
        holder.name.text = myItem.name
        holder.description.text = myItem.description
        holder.tarif.text = myItem.getFormattedPrice()

        val thumbnail = myItem.getThumbnail()
        if( thumbnail != null && thumbnail.isNotEmpty()) {
            Picasso.get()
                .load(thumbnail)
                .placeholder(R.drawable.ic_menu_search)
                .into(holder.image)
        }

        holder.layout.setOnClickListener {
            categoryClickListener.invoke(mPlats[position])
        }

        holder.layout.setOnLongClickListener {
            categoryLongClickListener.invoke(holder.deleteIcon)
            true
        }

        holder.deleteIcon.setOnClickListener {
            (it.getParent() as ViewManager).removeView(it)
            deletePlatAction.invoke(myItem)
        }

    }

    override fun getItemCount(): Int = mPlats.size

    class CategoryHolder(binding: ItemPlatBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.namePlat
        val description = binding.descriptionPlat
        val tarif = binding.prixPlat
        val image = binding.itemPicture
        val deleteIcon = binding.deleteButton

        val layout = binding.root
    }
}