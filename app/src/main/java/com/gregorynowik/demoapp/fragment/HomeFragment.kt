package com.gregorynowik.demoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.gregorynowik.demoapp.MainActivity
import com.gregorynowik.demoapp.R
import com.gregorynowik.demoapp.databinding.FragmentHomeBinding
import com.gregorynowik.demoapp.service.ShakeService

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var blueToothDeviceViewModel: BlueToothDeviceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        blueToothDeviceViewModel =
            ViewModelProviders.of(requireActivity()).get(BlueToothDeviceViewModel::class.java)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValues()

        if (mustShowDiscoveryDialog()) {
            showDiscoveryDialog()
        }
    }

    /**
     * Shows a dialog asking the user for bluetooth scan
     * if YES, launch device scan
     * if NO, close the application
     */
    private fun showDiscoveryDialog() {
        context?.let {
            MaterialDialog(it).show {
                title(R.string.home_scan_dialog_title)
                message(R.string.home_scan_dialog_message)
                positiveButton {
                    //dismiss dialog and launch bluetooth scan
                    dismiss()
                    navigateToBleDiscoveryFragment()
                }
                negativeButton {
                    //close the application
                    dismiss()
                    activity?.finish()
                }
            }
        }
    }

    /**
     * This function checks into the activity intent if it was opened by
     * clicking on the shake notification
     * @return dialog must be displayed to the user
     */
    private fun mustShowDiscoveryDialog(): Boolean {

        if (activity == null ||
            requireActivity().intent == null
            || requireActivity().intent.extras == null
        ) return false

        return requireActivity().intent!!.extras!!.getBoolean(
            ShakeService.EXTRA_OPENED_FROM_SHAKE_NOTIFICATION,
            false
        )
    }

    private fun observeValues() {
        blueToothDeviceViewModel.blueToothDeviceLiveData.observe(
            requireActivity(),
            Observer { bluetoothDevice ->
                // display bluetooth device info
                binding.bluetoothDevice = bluetoothDevice
            })
    }

    private fun navigateToBleDiscoveryFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_bleDiscoveryFragment)
    }


}