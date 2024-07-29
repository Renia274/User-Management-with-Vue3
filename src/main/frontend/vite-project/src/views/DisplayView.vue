<template>
  <div>
    <h2>Display Users</h2>
    <table class="user-table">
      <thead>
      <tr>
        <th class="header-black">
          Name
        </th>
        <th class="header-black">
          Surname
        </th>
        <th class="header-black">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.name }}</td>
        <td>{{ user.surname }}</td>
        <td>
          <button class="view-button" @click="viewUser(user.id)">View</button>
          <button class="delete-button" @click="deleteUser(user.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button @click="prevPage" :disabled="page === 0">Previous</button>
      <span>Page {{ page + 1 }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="page >= totalPages - 1">Next</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DisplayUsers',
  data() {
    return {
      users: [],       // Holds the users data
      page: 0,         // Current page number
      size: 10,        // Page size
      totalPages: 0    // Total number of pages
    };
  },
  async created() {
    await this.fetchUsers(); // Fetch users on component creation
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await fetch(`/api/v/users?page=${this.page}&size=${this.size}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        this.users = data.content; // Set the fetched data to users
        this.totalPages = data.totalPages; // Set the total pages
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
      }
    },
    async deleteUser(id) {
      try {
        const response = await fetch(`/api/v/users/${id}`, {
          method: 'DELETE',
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        await this.fetchUsers(); // Refetch users after deletion
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
      }
    },
    viewUser(id) {
      const routeData = this.$router.resolve({ name: 'ViewUser', params: { id } });
      window.open(routeData.href, '_blank');
    },
    nextPage() {
      if (this.page < this.totalPages - 1) {
        this.page++;
        this.fetchUsers();
      }
    },
    prevPage() {
      if (this.page > 0) {
        this.page--;
        this.fetchUsers();
      }
    }
  },
};
</script>

<style scoped>
.user-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.user-table th,
.user-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.user-table th.header-black {
  background-color: white;
  color: black;
  font-weight: bold;
  position: relative;
}

.view-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px;
}

.view-button:hover {
  background-color: #0056b3;
}

.delete-button {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.delete-button:hover {
  background-color: #c82333;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.pagination button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
  margin: 0 5px;
}

.pagination button:disabled {
  background-color: #6c757d;
  cursor: not-allowed;
}
</style>
