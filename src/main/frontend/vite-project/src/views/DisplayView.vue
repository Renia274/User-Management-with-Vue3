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
        this.$router.push({ path: '/error', query: { message: error.message } });
      }
    },
    async deleteUser(id) {
      try {
        // throw new Error('Forced error for testing');
        const response = await fetch(`/api/v/users/${id}`, {
          method: 'DELETE',
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        await this.fetchUsers(); // Refetch users after deletion
      } catch (error) {
        this.$router.push({ path: '/error', query: { message: error.message } });
      }
    },
    viewUser(id) {
      const routeData = this.$router.resolve({ name: 'ViewUser', params: { id } });
      window.open(routeData.href, '_blank'); // Open user details in a new tab
    },
    nextPage() {
      if (this.page < this.totalPages - 1) {
        this.page++;
        this.fetchUsers(); // Fetch users for the next page
      }
    },
    prevPage() {
      if (this.page > 0) {
        this.page--;
        this.fetchUsers(); // Fetch users for the previous page
      }
    }
  },
};
</script>



<template>
  <div>
    <h2>Display Users</h2>
    <!-- Table to display user information -->
    <table class="user-table">
      <thead>
      <tr>
        <th class="header-black">Name</th>
        <th class="header-black">Surname</th>
        <th class="header-black">Actions</th>
      </tr>
      </thead>
      <tbody>
      <!-- Loop through users and display their details -->
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.name }}</td>
        <td>{{ user.surname }}</td>
        <td>
          <!-- Button to view user details -->
          <button class="view-button" @click="viewUser(user.id)">View</button>
          <!-- Button to delete user -->
          <button class="delete-button" @click="deleteUser(user.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
    <!-- Pagination controls -->
    <div class="pagination">
      <button @click="prevPage" :disabled="page === 0">Previous</button>
      <span>Page {{ page + 1 }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="page >= totalPages - 1">Next</button>
    </div>
  </div>
</template>



<style scoped>

.user-table {
  width: 100%;
  border-collapse: collapse; /* Ensures borders are collapsed into a single border */
  margin-top: 20px; /* Space above the table */
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
}


.view-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px; /* Space between buttons */
}


.view-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 10px;
}


.view-button:hover {
  background-color: #0056b3;
}


.delete-button {
  background-color: #dc3545;
  color: white;
  border: none; /* Remove default border */
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}


.delete-button:hover {
  background-color: #c82333; /
}

/* Style for pagination controls */
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

/* Disabled state for pagination buttons */
.pagination button:disabled {
  background-color: #6c757d; /* Gray background for disabled buttons */
  cursor: not-allowed; /* Default cursor for disabled state */
}

</style>
