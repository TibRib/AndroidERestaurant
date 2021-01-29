package fr.isen.simon.androiderestaurant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.simon.androiderestaurant.databinding.CarrouselItemBinding


class CarrouselAdapter(
       private val images : List<String?>
): RecyclerView.Adapter<CarrouselAdapter.CarrouselHolder>() {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): CarrouselHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = CarrouselItemBinding.inflate(inflater, parent, false)

        return CarrouselHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CarrouselHolder, position: Int) {
        val img : String? = images[position]
        Picasso.get().load(img).into(holder.imgBanner)
    }

    override fun getItemCount(): Int = images.size

    class CarrouselHolder(binding: CarrouselItemBinding) : RecyclerView.ViewHolder(binding.root){
        val imgBanner = binding.imgBanner

        val layout = binding.root
    }

}