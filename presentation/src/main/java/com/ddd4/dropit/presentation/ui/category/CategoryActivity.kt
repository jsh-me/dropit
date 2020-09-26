package com.ddd4.dropit.presentation.ui.category

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ddd4.dropit.presentation.R
import com.ddd4.dropit.presentation.base.ui.BaseActivity
import com.ddd4.dropit.presentation.databinding.ActivityCategoryBinding
import com.ddd4.dropit.presentation.ui.add.AddActivity
import com.ddd4.dropit.presentation.ui.detailFolder.FolderItemDetailActivity
import com.ddd4.dropit.presentation.ui.folder.FolderAdapter
import com.ddd4.dropit.presentation.ui.folder.FolderViewModel
import com.ddd4.dropit.presentation.ui.moveFolder.MoveFolderActivity
import com.ddd4.dropit.presentation.util.Constants
import com.ddd4.dropit.presentation.util.hideButton
import com.ddd4.dropit.presentation.util.showButton
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CategoryActivity : BaseActivity<ActivityCategoryBinding>(R.layout.activity_category) {

    private val viewModel: CategoryViewModel by viewModels()
    private var categoryId = -1L


    override fun setBind() {
        binding.apply{
            categoryVM = viewModel
        }
        categoryId = getId(intent)
    }

    override fun setObserve() {

        viewModel.floatingButton.observe(this, Observer {
            startActivity(Intent(this, AddActivity::class.java))
            overridePendingTransition(R.anim.slide_down, R.anim.slide_up)
        })

        viewModel.nextButton.observe(this, Observer {
            startActivity(Intent(this, MoveFolderActivity::class.java))
        })

        viewModel.item.observe(this, Observer { item ->
            startActivity(Intent(this, FolderItemDetailActivity::class.java)
                .putExtra(Constants.EXTRA_NAME_ITEM_ID, item))
        })

        viewModel.backButton.observe(this, Observer {
            finish()
        })

        viewModel.selectImageButton.observe(this, Observer {
            binding.ibSelectImage.text = it
            when(it) {
                resources.getString(R.string.select) -> {
                    binding.folderFloatingButton.showButton()
                    binding.folderRectangleButton.hideButton()
                }
                resources.getString(R.string.cancel) -> {
                    binding.folderFloatingButton.hideButton()
                    binding.folderRectangleButton.showButton()
                }
            }
        })
    }

    private fun getId(intent: Intent): Long {
        return intent.getLongExtra(Constants.EXTRA_NAME_CATEGORY_ID, -1)
    }

    override fun onResume() {
        super.onResume()
        viewModel.start(categoryId)
        binding.rvDetailFolder.adapter?.notifyDataSetChanged()
    }
}