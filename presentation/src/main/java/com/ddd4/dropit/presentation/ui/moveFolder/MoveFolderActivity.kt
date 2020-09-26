package com.ddd4.dropit.presentation.ui.moveFolder

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ddd4.dropit.presentation.R
import com.ddd4.dropit.presentation.base.ui.BaseActivity
import com.ddd4.dropit.presentation.databinding.ActivityMoveFolderBinding
import com.ddd4.dropit.presentation.ui.dialog.DialogActivity
import com.ddd4.dropit.presentation.ui.main.MainActivity
import com.ddd4.dropit.presentation.util.Constants
import com.ddd4.dropit.presentation.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoveFolderActivity : BaseActivity<ActivityMoveFolderBinding>(R.layout.activity_move_folder) {

    private val viewModel: MoveFolderViewModel by viewModels()
    private var itemIdList = arrayListOf<Long>()

    override fun setBind() {
        binding.apply {
            moveFolderVM = viewModel
        }
        itemIdList = getIdList(intent)
        viewModel.start(itemIdList)
    }

    override fun setObserve() {
        viewModel.newFolderButton.observe(this, Observer {
            startActivityForResult(
                Intent(this, DialogActivity::class.java)
                    .putExtra(Constants.ITEM_ID, itemIdList)
                , 1500
            )
        })

        viewModel.moveFolder.observe(this, Observer {
            this.toast("사진 이동이 완료되었습니다.")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.backButton.observe(this, Observer {
            finish()

        })
    }

    private fun getIdList(intent: Intent): ArrayList<Long> {
        return intent.getIntegerArrayListExtra(Constants.ITEM_ID) as ArrayList<Long> ?: arrayListOf()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1500 && resultCode == 2000){
            binding.rvFolderList.adapter?.notifyDataSetChanged()
        }
    }
}