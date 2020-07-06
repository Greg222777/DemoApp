package com.gregorynowik.demoapp.adapter


import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gregorynowik.demoapp.databinding.ItemScanResultBinding


class ScanDeviceAdapter : RecyclerView.Adapter<ScanDeviceAdapter.ViewHolder>() {

    var scanResultList = mutableListOf<ScanResult>()

    var scanResultAdapterInterface : ScanResultAdapterInterface? = null

    inner class ViewHolder(private val binding: ItemScanResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(scanResult: ScanResult) {
            binding.scanResult = scanResult
            binding.executePendingBindings()
        }
    }

    interface ScanResultAdapterInterface{
        fun onScanResultClicked(scanResult: ScanResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemScanResultBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scanResultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { scanResultAdapterInterface?.onScanResultClicked(scanResultList[position]) }
       return holder.bind(scanResultList[position])
    }



}