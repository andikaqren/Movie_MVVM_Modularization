package com.andika.architecturecomponent.framework.presentation.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andika.architecturecomponent.databinding.DetailBottomShareBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailBottomSheetShare : BottomSheetDialogFragment() {

    private var _binding: DetailBottomShareBinding? = null
    private val binding get() = _binding!!

    lateinit var link: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailBottomShareBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDetailBottomCopy.setOnClickListener {
            copyClipboard()
        }
        binding.tvDetailBottomShare.setOnClickListener {
            shareContent()
        }
    }


    private fun copyClipboard() {
        val myClipboard =
            context?.let { it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
        val myClip: ClipData = ClipData.newPlainText("note_copy", link)
        myClipboard?.setPrimaryClip(myClip)
        Toast.makeText(context, "Success Copy to Clipboard", Toast.LENGTH_LONG).show()
        dismiss()
    }

    private fun shareContent() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT, link
        )
        sendIntent.type = "text/plain"
        context?.startActivity(sendIntent)
        dismiss()
    }


}