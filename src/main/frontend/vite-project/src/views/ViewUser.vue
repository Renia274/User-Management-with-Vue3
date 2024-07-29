<template>
  <div class="user-profile">
    <h1>User Profile</h1>
    <div v-if="user">
      <p class="user-detail">Name: <span class="highlight">{{ user.name }}</span></p>
      <p class="user-detail">Surname: <span class="highlight">{{ user.surname }}</span></p>
      <p class="user-detail">Gender: <span class="highlight">{{ user.gender === 'M' ? 'Male' : 'Female' }}</span></p>
      <p class="user-detail">Birthdate: <span class="highlight">{{ formatDate(user.birthdate) }}</span></p>
      <p class="user-detail">Work Address: <span class="highlight">{{ user.address.workAddress }}</span></p>
      <p class="user-detail">Home Address: <span class="highlight">{{ user.address.homeAddress }}</span></p>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ViewUser',
  props: ['id'],
  data() {
    return {
      user: null
    };
  },
  async created() {
    await this.fetchUser();
  },
  methods: {
    async fetchUser() {
      try {
        const response = await fetch(`/api/v/users/${this.id}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        this.user = await response.json();
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
      }
    },
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString();
    }
  }
};
</script>

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
