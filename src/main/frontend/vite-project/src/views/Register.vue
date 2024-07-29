<template>
  <!-- Main container for the registration form -->
  <div class="register-container">
    <h1>Register New User</h1>
    <!-- Form to register a new user, prevents default submission to handle it manually -->
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="name">Name:<span class="required-asterisk">*</span></label>
        <!-- Input for user's name, calls validateName on blur -->
        <input type="text" v-model="user.name" @blur="validateName" required>
        <!-- Displays validation message for name if any -->
        <div v-if="nameMessage" class="validation-message">{{ nameMessage }}</div>
      </div>
      <div class="form-group">
        <label for="surname">Surname:<span class="required-asterisk">*</span></label>
        <!-- Input for user's surname, calls validateSurname on blur -->
        <input type="text" v-model="user.surname" @blur="validateSurname" required>
        <!-- Displays validation message for surname if any -->
        <div v-if="surnameMessage" class="validation-message">{{ surnameMessage }}</div>
      </div>
      <div class="form-group">
        <label for="gender">Gender:<span class="required-asterisk">*</span></label>
        <!-- Select for user's gender, calls validateGender on blur -->
        <select v-model="user.gender" @blur="validateGender" required>
          <option value="" disabled>Select Gender</option>
          <option value="M">Male</option>
          <option value="F">Female</option>
        </select>
        <!-- Displays validation message for gender if any -->
        <div v-if="genderMessage" class="validation-message">{{ genderMessage }}</div>
      </div>
      <div class="form-group">
        <label for="birthdate">Birthdate:<span class="required-asterisk">*</span></label>
        <!-- Input for user's birthdate, calls validateBirthdate on blur -->
        <input type="date" v-model="user.birthdate" @blur="validateBirthdate" required>
        <!-- Displays validation message for birthdate if any -->
        <div v-if="birthdateMessage" class="validation-message">{{ birthdateMessage }}</div>
      </div>
      <div class="form-group">
        <label for="workAddress">Work Address:</label>
        <textarea v-model="user.address.workAddress"></textarea>
      </div>
      <div class="form-group">
        <label for="homeAddress">Home Address:</label>
        <!-- Textarea for user's home address -->
        <textarea v-model="user.address.homeAddress"></textarea>
      </div>
      <div class="form-group button-container">
        <button type="submit">Register</button>
      </div>
    </form>
    <!-- Displays success or error message based on form submission -->
    <div v-if="message" :class="{'message': true, 'success': messageType === 'success', 'error': messageType === 'error'}">{{ message }}</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

// Reactive reference to the user data
const user = ref({
  name: '',
  surname: '',
  gender: '',
  birthdate: '',
  address: {
    workAddress: '',
    homeAddress: ''
  }
})

// Reactive references for validation and message handling
const message = ref(null)
const messageType = ref(null)
const nameMessage = ref(null)
const surnameMessage = ref(null)
const genderMessage = ref(null)
const birthdateMessage = ref(null)
const router = useRouter()

// Function to validate the name field
const validateName = () => {
  const nameRegex = /^[A-Z][a-zA-Z]*$/;
  if (user.value.name && !nameRegex.test(user.value.name)) {
    nameMessage.value = 'Name must start with a capital letter and contain only letters.';
  } else {
    nameMessage.value = null;
  }
}

// Function to validate the surname field
const validateSurname = () => {
  const surnameRegex = /^[A-Z][a-zA-Z]*$/;
  if (user.value.surname && !surnameRegex.test(user.value.surname)) {
    surnameMessage.value = 'Surname must start with a capital letter and contain only letters.';
  } else {
    surnameMessage.value = null;
  }
}

// Function to validate the gender field
const validateGender = () => {
  if (!user.value.gender) {
    genderMessage.value = 'Gender is required.';
  } else {
    genderMessage.value = null;
  }
}

// Function to validate the birthdate field
const validateBirthdate = () => {
  if (!user.value.birthdate) {
    birthdateMessage.value = 'Birthdate is required.';
  } else {
    birthdateMessage.value = null;
  }
}

// Function to handle form submission
const submitForm = async () => {
  validateName();
  validateSurname();
  validateGender();
  validateBirthdate();

  // Check for validation errors
  if (nameMessage.value || surnameMessage.value || genderMessage.value || birthdateMessage.value) {
    message.value = 'Please fill out all mandatory fields correctly.';
    messageType.value = 'error';
    return;
  }

  try {
    // Send the user data to the server
    const response = await fetch('/api/v/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user.value)
    });
    if (response.ok) {
      message.value = 'User registered successfully!';
      messageType.value = 'success';
      // Optionally redirect to another page
      // setTimeout(() => {
      //   router.push('/users');
      // }, 2000);
    } else {
      // Parse and display server-side error message
      const errorData = await response.json();
      message.value = `Failed to register user. ${errorData.message}`;
      messageType.value = 'error';
    }
  } catch (error) {
    // Display client-side error message
    message.value = 'An error occurred: ' + error.message;
    messageType.value = 'error';
  }
}
</script>

<style scoped>
/* Container for the registration form */
.register-container {
  max-width: 600px;
  margin: 0 auto;
}

/* Style for form groups */
.form-group {
  margin-bottom: 15px;
}


label {
  display: block;
  margin-bottom: 5px;
}

/* Style for required field asterisk */
.required-asterisk {
  color: red;
  margin-left: 5px;
}

/* Style for form controls */
input, select, textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}


.button-container {
  display: flex;
  justify-content: center;
}


button {
  padding: 10px 15px;
  background-color: green;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}


button:hover {
  background-color: darkgreen;
}

/* Style for message display */
.message {
  margin-top: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  background-color: #f9f9f9;
  text-align: center;
}


.message.success {
  border-color: green;
  color: green;
}


.message.error {
  border-color: red;
  color: red;
}

/* Style for validation error messages */
.validation-message {
  color: red;
  font-size: 14px;
  margin-top: 5px;
}
</style>
