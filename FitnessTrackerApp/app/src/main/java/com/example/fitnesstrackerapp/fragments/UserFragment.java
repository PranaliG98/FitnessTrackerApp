package com.example.fitnesstrackerapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesstrackerapp.LoginActivity;
import com.example.fitnesstrackerapp.R;

public class UserFragment extends Fragment {

    private EditText editTextName, editTextEmail, editTextPassword, editTextAge, editTextWeight;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale, radioButtonOther;
    private Button buttonEdit, buttonSignout;
    private ImageView profileImage;

    private boolean isEditing = false;  // Track if editing mode is active

    private static final String PREFS_NAME = "UserProfile";
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Initialize views
        editTextName = view.findViewById(R.id.editTextname);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        radioButtonMale = view.findViewById(R.id.radioMale);
        radioButtonFemale = view.findViewById(R.id.radioFemale);
        radioButtonOther = view.findViewById(R.id.radioOther);
        buttonEdit = view.findViewById(R.id.buttonEdit);
        buttonSignout = view.findViewById(R.id.buttonSignout);
        profileImage = view.findViewById(R.id.imageView7);

        // Initialize SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load saved user data
        loadUserData();

        // Set initial states of EditTexts
        setEditTextEnabled(false); // Make EditTexts uneditable by default

        // Set listeners for buttons
        buttonEdit.setOnClickListener(v -> toggleEditProfile());
        buttonSignout.setOnClickListener(v -> signOut());

        // Set listener for profile image click
        profileImage.setOnClickListener(v -> changeProfilePicture());

        return view;
    }

    // Method to toggle edit profile state
    private void toggleEditProfile() {
        isEditing = !isEditing; // Toggle editing state
        setEditTextEnabled(isEditing); // Enable or disable EditTexts based on editing state

        if (isEditing) {
            buttonEdit.setText("Save"); // Change button text to "Save"
        } else {
            buttonEdit.setText("Edit Profile"); // Change back to "Edit Profile"
            saveProfileData(); // Save data when editing mode is exited
        }
    }

    // Method to set EditText fields enabled or disabled
    private void setEditTextEnabled(boolean isEnabled) {
        editTextName.setEnabled(isEnabled);
        editTextEmail.setEnabled(isEnabled);
        editTextPassword.setEnabled(isEnabled);
        editTextAge.setEnabled(isEnabled);
        editTextWeight.setEnabled(isEnabled);
    }

    // Method to load user data from SharedPreferences
    private void loadUserData() {
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
        String age = sharedPreferences.getString("age", "");
        String weight = sharedPreferences.getString("weight", "");

        editTextName.setText(name);
        editTextEmail.setText(email);
        editTextPassword.setText(password);
        editTextAge.setText(age);
        editTextWeight.setText(weight);

        // Set gender radio button based on saved data
        String gender = sharedPreferences.getString("gender", "Male");
        if (gender.equals("Male")) {
            radioButtonMale.setChecked(true);
        } else if (gender.equals("Female")) {
            radioButtonFemale.setChecked(true);
        } else {
            radioButtonOther.setChecked(true);
        }
    }

    // Method to save profile data to SharedPreferences
    private void saveProfileData() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String age = editTextAge.getText().toString();
        String weight = editTextWeight.getText().toString();

        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        String gender = selectedGenderId == radioButtonMale.getId() ? "Male" :
                selectedGenderId == radioButtonFemale.getId() ? "Female" : "Other";

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putString("age", age);
        editor.putString("weight", weight);
        editor.putString("gender", gender);
        editor.apply(); // Save changes asynchronously

        Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    // Method to handle profile picture change
    private void changeProfilePicture() {
        // Implement logic to change the profile picture (e.g., open a gallery or camera)
        Toast.makeText(getActivity(), "Change Profile Picture clicked", Toast.LENGTH_SHORT).show();
    }

    // Method to handle sign-out
    private void signOut() {
        // Implement sign-out logic here (e.g., Firebase sign out)
        Toast.makeText(getActivity(), "Signed out successfully", Toast.LENGTH_SHORT).show();
        // Redirect to login screen
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
