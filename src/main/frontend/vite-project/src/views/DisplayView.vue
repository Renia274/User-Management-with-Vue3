<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';


const router = useRouter();

// Reactive state variables for user data and pagination
const users = ref([]);
const page = ref(0);
const size = ref(10); // Number of users per page
const totalPages = ref(0); // Total number of pages

// Computed properties to determine if the current page is the first or last page
const isFirstPage = computed(() => page.value === 0);
const isLastPage = computed(() => page.value >= totalPages.value - 1);

// Function to fetch users from the server and handle errors
const fetchUsers = async () => {
  try {
    // Fetch users from the API
    const response = await fetch(`/api/v/users?page=${page.value}&size=${size.value}`);
    if (!response.ok) throw new Error('Network response was not ok');
    const data = await response.json();
    users.value = data.content; // Update the users list
    totalPages.value = data.totalPages; // Update total pages
  } catch (error) {
    // Redirect to error page if fetching fails
    await router.push({ path: '/error', query: { message: error.message } });
  }
};

// Function to delete a user and refresh the user list
const deleteUser = async (id) => {
  try {
    // Send DELETE request to the server
    const response = await fetch(`/api/v/users/${id}`, { method: 'DELETE' });
    if (!response.ok) throw new Error('Network response was not ok');
    await fetchUsers(); // Refetch users after deletion
  } catch (error) {
    // Redirect to error page if deletion fails
    await router.push({ path: '/error', query: { message: error.message } });
  }
};

// Function to view user details in a new tab
const viewUser = (id) => {
  // Resolve the route for the user details page
  const routeData = router.resolve({ name: 'ViewUser', params: { id } });
  window.open(routeData.href, '_blank'); // Open the resolved route in a new tab
};

// Function to go to the next page and fetch users
const nextPage = async () => {
  if (!isLastPage.value) {
    page.value++;
    await fetchUsers(); // Fetch users for the next page
  }
};

// Function to go to the previous page and fetch users
const prevPage = async () => {
  if (!isFirstPage.value) {
    page.value--;
    await fetchUsers(); // Fetch users for the previous page
  }
};

// Fetch users when the component is mounted
onMounted(() => {
  fetchUsers(); // Initial fetch when the component is first loaded
});
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
      <!-- Button to go to the previous page -->
      <button @click="prevPage" :disabled="isFirstPage">Previous</button>
      <!-- Display current page and total pages -->
      <span>Page {{ page + 1 }} of {{ totalPages }}</span>
      <!-- Button to go to the next page -->
      <button @click="nextPage" :disabled="isLastPage">Next</button>
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

.header-black {
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
  cursor: not-allowed; /* Default cursor for disabled state */
}
</style>
