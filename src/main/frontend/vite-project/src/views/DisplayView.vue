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

<style scoped>
/* Style for the user table */
.user-table {
  width: 100%;
  border-collapse: collapse; /* Ensures borders are collapsed into a single border */
  margin-top: 20px; /* Space above the table */
}

/* Style for table headers and cells */
.user-table th,
.user-table td {
  border: 1px solid #ddd; /* Light gray border */
  padding: 8px; /* Padding inside cells */
  text-align: left; /* Align text to the left */
}

/* Style for table header cells */
.user-table th.header-black {
  background-color: white; /* White background for headers */
  color: black; /* Black text color for headers */
  font-weight: bold; /* Bold text for headers */
}

/* Style for view buttons */
.view-button {
  background-color: #007bff; /* Blue background */
  color: white; /* White text */
  border: none; /* Remove default border */
  padding: 5px 10px; /* Padding inside button */
  border-radius: 4px; /* Rounded corners */
  cursor: pointer; /* Pointer cursor on hover */
  margin-right: 10px; /* Space between buttons */
}

/* Hover effect for view buttons */
.view-button:hover {
  background-color: #0056b3; /* Darker blue on hover */
}

/* Style for delete buttons */
.delete-button {
  background-color: #dc3545; /* Red background */
  color: white; /* White text */
  border: none; /* Remove default border */
  padding: 5px 10px; /* Padding inside button */
  border-radius: 4px; /* Rounded corners */
  cursor: pointer; /* Pointer cursor on hover */
}

/* Hover effect for delete buttons */
.delete-button:hover {
  background-color: #c82333; /* Darker red on hover */
}

/* Style for pagination controls */
.pagination {
  margin-top: 20px; /* Space above pagination controls */
  display: flex; /* Flexbox layout for alignment */
  justify-content: center; /* Center the content horizontally */
  align-items: center; /* Center the content vertically */
}

/* Style for pagination buttons */
.pagination button {
  background-color: #007bff; /* Blue background */
  color: white; /* White text */
  border: none; /* Remove default border */
  padding: 5px 10px; /* Padding inside button */
  border-radius: 4px; /* Rounded corners */
  cursor: pointer; /* Pointer cursor on hover */
  margin: 0 5px; /* Space between buttons */
}

/* Disabled state for pagination buttons */
.pagination button:disabled {
  background-color: #6c757d; /* Gray background for disabled buttons */
  cursor: not-allowed; /* Default cursor for disabled state */
}
</style>
