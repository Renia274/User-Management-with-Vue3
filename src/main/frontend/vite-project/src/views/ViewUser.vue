<script>
export default {
  name: 'ViewUser',
  props: ['id'], // Used for identifying the user to be fetched
  data() {
    return {
      user: null // Holds the user data fetched from the backend
    };
  },
  async created() {
    // Fetch user data when the component is created
    await this.fetchUser();
  },
  methods: {
    async fetchUser() {
      // Fetch user data from the backend
      try {
        // Make a GET request to the  endpoint for the specific user
        const response = await fetch(`/api/v/users/${this.id}`);
        if (!response.ok) {
          // Throw an error if the response status is not OK (status does not belong to 200-299 range)
          throw new Error('Network response was not ok');
        }
        // Parse the JSON response and assign it to the user data property
        this.user = await response.json();
      } catch (error) {
        // Log any errors encountered during the fetch operation
        console.error('There was a problem with the fetch operation:', error);
      }
    },
    formatDate(dateString) {
      // Format the date string into a readable format
      if (!dateString) return ''; // Return an empty string if no date is provided
      const date = new Date(dateString); // Convert the date string to a Date object
      return date.toLocaleDateString(); // Return the formatted date string
    }
  }
};
</script>

<template>
  <div class="user-profile">
    <h1>User Profile</h1>
    <!-- Display user details if the user data is available -->
    <div v-if="user">
      <p class="user-detail">Name: <span class="highlight">{{ user.name }}</span></p>
      <p class="user-detail">Surname: <span class="highlight">{{ user.surname }}</span></p>
      <p class="user-detail">Gender: <span class="highlight">{{ user.gender === 'M' ? 'Male' : 'Female' }}</span></p>
      <p class="user-detail">Birthdate: <span class="highlight">{{ formatDate(user.birthdate) }}</span></p>
      <p class="user-detail">Work Address: <span class="highlight">{{ user.address.workAddress }}</span></p>
      <p class="user-detail">Home Address: <span class="highlight">{{ user.address.homeAddress }}</span></p>
    </div>
    <!-- Display a loading message if the user data is not yet available -->
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<style scoped>
.user-profile {
  margin-left: 20px;
  margin-top: 20px;
}

.user-detail {
  margin-bottom: 10px;
}

.highlight {
  color: #007bff;
}

p {
  margin-left: 10px;
}
</style>
