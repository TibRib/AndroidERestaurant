package fr.isen.simon.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.isen.simon.androiderestaurant.databinding.ActivityCategoryActivityBinding
import fr.isen.simon.androiderestaurant.databinding.ItemPlatBinding

class CategoryAdapter (private val mPlats : List<Plat>): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemPlatBinding.inflate(inflater,parent,false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryHolder, position: Int) {
        val myItem = mPlats[position]
        holder.name.text = myItem.name
        holder.description.text = myItem.description
        holder.tarif.text = myItem.tarif.toString()+"€"

    }

    override fun getItemCount(): Int = mPlats.size

    class CategoryHolder(binding : ItemPlatBinding) : RecyclerView.ViewHolder(binding.root){
        val name = binding.namePlat
        val description = binding.descriptionPlat
        val tarif = binding.prixPlat
    }
}