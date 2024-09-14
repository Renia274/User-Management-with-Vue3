<script setup>
import { ref, computed } from 'vue';

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
});

// Reactive reference for messages
const message = ref(null);
const messageType = ref(null);

// Function to format the date to yyyy-MM-dd
const formatDateToYYYYMMDD = (dateString) => {
  const date = new Date(dateString);
  return `${date.getFullYear()}-${('0' + (date.getMonth() + 1)).slice(-2)}-${('0' + date.getDate()).slice(-2)}`;
};

// Function to handle form submission
const submitForm = async () => {
  // Format the birthdate to yyyy-MM-dd before sending
  if (user.value.birthdate) {
    user.value.birthdate = formatDateToYYYYMMDD(user.value.birthdate);
  }

  try {
    // Send the user data to the server
    const response = await fetch('/api/v/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(user.value)
    });

    if (response.ok) {
      message.value = 'User registered successfully!';
      messageType.value = 'success';

      // Reset the form fields after successful registration
      resetForm();
    } else {
      const errorData = await response.json();
      message.value = `Failed to register user. ${errorData.message}`;
      messageType.value = 'error';
    }
  } catch (error) {
    message.value = 'An error occurred: ' + error.message;
    messageType.value = 'error';
  }
};

// Function to reset the form fields
const resetForm = () => {
  user.value = {
    name: '',
    surname: '',
    gender: '',
    birthdate: '',
    address: {
      workAddress: '',
      homeAddress: ''
    }
  };
};

// Computed property for message type
const messageClass = computed(() => ({
  message: true,
  success: messageType.value === 'success',
  error: messageType.value === 'error'
}));
</script>



<template>
  <div class="register-container">
    <h1>Register New User</h1>
    <form @submit.prevent="submitForm">
      <!-- Name Input -->
      <div class="form-group">
        <label for="name">Name:<span class="required-asterisk">*</span></label>
        <input type="text" v-model="user.name" required />
      </div>

      <!-- Surname Input -->
      <div class="form-group">
        <label for="surname">Surname:<span class="required-asterisk">*</span></label>
        <input type="text" v-model="user.surname" required />
      </div>

      <!-- Gender Select -->
      <div class="form-group">
        <label for="gender">Gender:<span class="required-asterisk">*</span></label>
        <select v-model="user.gender" required>
          <option value="" disabled>Select Gender</option>
          <option value="M">Male</option>
          <option value="F">Female</option>
        </select>
      </div>

      <!-- Birthdate Input -->
      <div class="form-group">
        <label for="birthdate">Birthdate:<span class="required-asterisk">*</span></label>
        <input type="date" v-model="user.birthdate" required />
      </div>

      <!-- Work Address Textarea -->
      <div class="form-group">
        <label for="workAddress">Work Address:</label>
        <textarea v-model="user.address.workAddress"></textarea>
      </div>

      <!-- Home Address Textarea -->
      <div class="form-group">
        <label for="homeAddress">Home Address:</label>
        <textarea v-model="user.address.homeAddress"></textarea>
      </div>

      <!-- Submit Button -->
      <div class="form-group">
        <button type="submit" class="register-button">Register</button>
      </div>
    </form>

    <!-- Display success or error message -->
    <div v-if="message" :class="messageClass">{{ message }}</div>
  </div>
</template>



<style scoped>
.register-container {
  max-width: 400px;
  margin: 0 auto;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
  text-align: center;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.required-asterisk {
  color: red;
  margin-left: 5px;
}

input, select, textarea {
  width: 100%;
  max-width: 300px;
  margin: 0 auto;
  padding: 8px;
  box-sizing: border-box;
  display: block;
}

textarea {
  resize: vertical;
}

.register-button {
  padding: 10px 20px;
  background-color: green;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.register-button:hover {
  background-color: darkgreen;
}

.message {
  margin-top: 20px;
  padding: 10px;
  text-align: center;
}

.success {
  color: green;
}

.error {
  color: red;
}
</style>
