package com.synrgy.movieapp.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bumptech.glide.Glide
import com.synrgy.movieapp.R
import com.synrgy.movieapp.database.UserPreferences
import com.synrgy.movieapp.worker.UploadImageWorker

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var etUsername: EditText
    private lateinit var etFullName: EditText
    private lateinit var etDateOfBirth: EditText
    private lateinit var etAddress: EditText
    private lateinit var userPreferences: UserPreferences

    private val pickImageRequestCode = 1
    private var profileImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        setHasOptionsMenu(false)

        userPreferences = UserPreferences(requireContext())

        profileImage = view.findViewById(R.id.profileImage)
        etUsername = view.findViewById(R.id.etUsername)
        etFullName = view.findViewById(R.id.etFullName)
        etDateOfBirth = view.findViewById(R.id.etDateOfBirth)
        etAddress = view.findViewById(R.id.etAddress)
        val btnUploadImage: Button = view.findViewById(R.id.btnUploadImage)
        val btnUpdate: Button = view.findViewById(R.id.btnUpdate)
        val btnLogout: Button = view.findViewById(R.id.btnLogout)

        btnUploadImage.setOnClickListener {
            openGalleryForImage()
        }

        btnUpdate.setOnClickListener {
            // Update user info logic
            updateUserProfile()
        }

        btnLogout.setOnClickListener {
            // Logout logic
            logoutUser()
        }

        loadUserProfile()

        return view
    }

    private fun loadUserProfile() {
        etUsername.setText(userPreferences.username)
        etFullName.setText(userPreferences.fullName)
        etDateOfBirth.setText(userPreferences.dateOfBirth)
        etAddress.setText(userPreferences.address)
        userPreferences.profileImageUrl?.let {
            Glide.with(this).load(it).into(profileImage)
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, pickImageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK) {
            profileImageUri = data?.data
            profileImageUri?.let {
                uploadImage(it)
            }
        }
    }

    private fun uploadImage(uri: Uri) {
        val workManager = WorkManager.getInstance(requireContext())
        val uploadRequest = OneTimeWorkRequestBuilder<UploadImageWorker>()
            .setInputData(workDataOf("imageUri" to uri.toString()))
            .build()

        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id).observe(viewLifecycleOwner, Observer { workInfo ->
            if (workInfo != null && workInfo.state.isFinished) {
                val imageUrl = workInfo.outputData.getString("imageUrl")
                userPreferences.profileImageUrl = imageUrl
                Glide.with(this).load(imageUrl).into(profileImage)
            }
        })
    }

    private fun updateUserProfile() {
        userPreferences.username = etUsername.text.toString()
        userPreferences.fullName = etFullName.text.toString()
        userPreferences.dateOfBirth = etDateOfBirth.text.toString()
        userPreferences.address = etAddress.text.toString()
        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }

    private fun logoutUser() {
        // Handle user logout logic
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Optionally, inflate a custom menu for this fragment
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
