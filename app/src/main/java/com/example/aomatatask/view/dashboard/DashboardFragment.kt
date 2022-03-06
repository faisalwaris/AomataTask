package com.example.aomatatask.view.dashboard

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aomatatask.R
import com.example.aomatatask.base.BaseFragment
import com.example.aomatatask.databinding.FragmentDashboardBinding
import com.example.aomatatask.databinding.NoDataFoundBinding
import com.example.aomatatask.network.networkApiResponse.PixabayPhotoResponse
import com.example.aomatatask.util.*
import com.example.aomatatask.view.activity.SingleActivity
import com.example.aomatatask.view.adapter.DashboardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModelDashBoard: ViewModelDashBoard by viewModels()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var galleryAdapter: DashboardAdapter
    private var galleryList: MutableList<PixabayPhotoResponse> = ArrayList()
    private var imgSelectedType: String? = null
    private lateinit var noDataFoundBinding: NoDataFoundBinding

    private lateinit var singleActivity: SingleActivity
    private var projectionForImages = arrayOf(
        MediaStore.MediaColumns.DATA,
    )

    private var orderBy = MediaStore.Images.Media.DATE_TAKEN

    override fun initView() {
        binding = FragmentDashboardBinding.bind(requireView())
        noDataFoundBinding = binding.noDataFound
        if (imgSelectedType.isNullOrBlank()) getString(R.string.internet)
        setGalleryAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        singleActivity = requireActivity() as SingleActivity
        singleActivity.setTopTitle(getString(R.string.app_name))
        singleActivity.getGridCellView().setOnClickListener(this)
        singleActivity.getImageSelectionView().setOnClickListener(this)

    }

    override fun initClickListener() {
    }

    override fun attachViewModel() {
        setViewModel(viewModelDashBoard)
        viewModelDashBoard.pixabayImagesList.observe(viewLifecycleOwner) {
            it.getData()?.let { imagesListResponse ->
                if (imagesListResponse.isNotEmpty()) {
                    galleryList.clear()
                    galleryList.addAll(imagesListResponse)
                    galleryAdapter.notifyDataSetChanged()

                    noDataFoundBinding.showHideNoData(
                        getString(R.string.no_data_found),
                        R.drawable.ic_no_data_found,
                        galleryList.size
                    )
                }
            }
        }

    }

    override fun onClick(view: View?) {
        when (view) {
            singleActivity.getGridCellView() -> {
                showAlertForGridSelection()
            }
            singleActivity.getImageSelectionView() -> {
                showImageSelectionAlert()
            }
        }
    }


    private fun setGalleryAdapter() {
        binding.rcvImages.layoutManager = GridLayoutManager(requireContext(), 2)
        galleryAdapter = DashboardAdapter(galleryList) { selectedIndex ->
            showAlertDialog(selectedIndex)
        }
        binding.rcvImages.adapter = galleryAdapter
    }


    private fun showAlertForGridSelection() {
        val gridSelectionAlert = GridSelectionAlert { selectedNumber ->
            (binding.rcvImages.layoutManager as GridLayoutManager).spanCount = selectedNumber
            binding.rcvImages.adapter?.notifyDataSetChanged()
        }
        gridSelectionAlert.show(requireActivity().supportFragmentManager, "")
    }


    private fun showImageSelectionAlert() {
        val imageSelectionAlert = ImageSelectionAlert(
        ) { selectedType ->
            if (selectedType.isNullOrEmpty().not()) {
                when (selectedType) {
                    getString(R.string.internet) -> {
                        if (selectedType != imgSelectedType) {
                            imgSelectedType = getString(R.string.internet)
                            viewModelDashBoard.fetchImages()
                        }
                    }

                    getString(R.string.gallery) -> {
                        if (selectedType != imgSelectedType) {
                            permissionCheckForGalleryCamera()
                        }
                    }

                }
            }
        }
        imageSelectionAlert.show(requireActivity().supportFragmentManager, "")
    }

    private fun permissionCheckForGalleryCamera() {
        if (this.checkPermission(
                requireContext(), arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
                AppConstant.GALLERY_CAMERA_PERMISSION_CODE
            )
        ) {
            getContentFromGallery()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            AppConstant.GALLERY_CAMERA_PERMISSION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContentFromGallery()
                }
            }

            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }


    @SuppressLint("Range")
    private fun getContentFromGallery() {
        imgSelectedType = getString(R.string.gallery)
        galleryList.clear()
        lifecycleScope.launch(Dispatchers.IO) {
            val cursorForImages = requireContext().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projectionForImages,
                null,
                null,
                "$orderBy DESC"
            )
            while (cursorForImages!!.moveToNext()) {
                galleryList.add(
                    PixabayPhotoResponse(
                        cursorForImages.getString(
                            cursorForImages.getColumnIndex(
                                projectionForImages[0]
                            )
                        )
                    )
                )
            }
            cursorForImages.close()
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                galleryAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showAlertDialog(position: Int) {
        if (galleryList[position].previewURL != null) {
            val alertDialog = BasicAlertDialog {
                findNavController(requireView()).navigate(
                    DashboardFragmentDirections.actionDashBoardFragmentToThumbnailDetailFragment(
                        galleryList[position].previewURL!!
                    )
                )
            }
            alertDialog.setAlertData(
                BasicAlertDialogModel(
                    title = getString(R.string.alert),
                    description = getString(R.string.you_want_to_open),
                    negativeButtonTitle = getString(R.string.no),
                    positiveButtonTitle = getString(R.string.yes)
                )
            )
            alertDialog.show(requireActivity().supportFragmentManager, "")
        }
    }
}