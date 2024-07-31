<script setup>
import { ref, computed, defineEmits } from 'vue';

// Define emits for the Register component
const emit = defineEmits(['submit', 'validation']);

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
const validationMessages = ref({
  name: null,
  surname: null,
  gender: null,
  birthdate: null
});

// Function to validate a field and set validation message
const validateField = (field, regex, errorMessage) => {
  if (user.value[field] && !regex.test(user.value[field])) {
    validationMessages.value[field] = errorMessage;
  } else {
    validationMessages.value[field] = null;
  }
  emit('validation', {field, message: validationMessages.value[field]});
};

// Validate each field
const validateName = () => validateField(
    'name',
    /^[A-Z][a-zA-Z]*$/,
    'Name must start with a capital letter and contain only letters.'
);

const validateSurname = () => validateField(
    'surname',
    /^[A-Z][a-zA-Z]*$/,
    'Surname must start with a capital letter and contain only letters.'
);

const validateGender = () => {
  validationMessages.value.gender = user.value.gender ? null : 'Gender is required.';
  emit('validation', {field: 'gender', message: validationMessages.value.gender});
};

const validateBirthdate = () => {
  validationMessages.value.birthdate = user.value.birthdate ? null : 'Birthdate is required.';
  emit('validation', {field: 'birthdate', message: validationMessages.value.birthdate});
};

// Function to handle form submission
const submitForm = async () => {
  // Validate fields
  validateName();
  validateSurname();
  validateGender();
  validateBirthdate();

  // Check for validation errors
  if (Object.values(validationMessages.value).some(msg => msg !== null)) {
    message.value = 'Please fill out all mandatory fields correctly.';
    messageType.value = 'error';
    emit('submit', {success: false, message: message.value});
    return;
  }

  try {
    // Send the user data to the server
    const response = await fetch('/api/v/register', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(user.value)
    });

    if (response.ok) {
      message.value = 'User registered successfully!';
      messageType.value = 'success';
    } else {
      // Parse and display server-side error message
      const errorData = await response.json();
      message.value = `Failed to register user. ${errorData.message}`;
      messageType.value = 'error';
    }
    emit('submit', {success: response.ok, message: message.value});
  } catch (error) {
    // Display client-side error message
    message.value = 'An error occurred: ' + error.message;
    messageType.value = 'error';
    emit('submit', {success: false, message: message.value});
  }
};

// Computed properties for message type
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
        <input type="text" v-model="user.name" @blur="validateName" required>
        <div v-if="validationMessages.name" class="validation-message">{{ validationMessages.name }}</div>
      </div>

      <!-- Surname Input -->
      <div class="form-group">
        <label for="surname">Surname:<span class="required-asterisk">*</span></label>
        <input type="text" v-model="user.surname" @blur="validateSurname" required>
        <div v-if="validationMessages.surname" class="validation-message">{{ validationMessages.surname }}</div>
      </div>

      <!-- Gender Select -->
      <div class="form-group">
        <label for="gender">Gender:<span class="required-asterisk">*</span></label>
        <select v-model="user.gender" @blur="validateGender" required>
          <option value="" disabled>Select Gender</option>
          <option value="M">Male</option>
          <option value="F">Female</option>
        </select>
        <div v-if="validationMessages.gender" class="validation-message">{{ validationMessages.gender }}</div>
      </div>

      <!-- Birthdate Input -->
      <div class="form-group">
        <label for="birthdate">Birthdate:<span class="required-asterisk">*</span></label>
        <input type="date" v-model="user.birthdate" @blur="validateBirthdate" required>
        <div v-if="validationMessages.birthdate" class="validation-message">{{ validationMessages.birthdate }}</div>
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
      <div class="form-group button-container">
        <button type="submit">Register</button>
      </div>
    </form>

    <!-- Display success or error message -->
    <div v-if="message" :class="messageClass">{{ message }}</div>
  </div>
</template>

<style scoped>
.register-container {
  max-width: 600px;
  margin: 0 auto;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

.required-asterisk {
  color: red;
  margin-left: 5px;
}

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
  text-align: center;
}

.message.success {
  color: green;
  border:none;
}

.message.error {
  color: red;
  border:none;
}

.validation-message {
  color: red;
  font-size: 14px;
  margin-top: 5px;
}
</style>

