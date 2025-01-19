const taskForm = document.getElementById('taskForm');
const taskList = document.getElementById('taskList');

const apiBaseUrl = 'http://localhost:8080/tasks'; // Update this with your EC2 instance's public IP

// Get the current user name and show on nav bar
fetch('/tasks/api/current-user')
	.then(response => response.json())
	.then(data => {
		document.getElementById('loggedInUser').textContent = (data.username || 'Guest').toUpperCase();
	})
	.catch(() => {
		document.getElementById('loggedInUser').textContent = 'Guest';
	});

// Fetch and display tasks
async function fetchTasks() {
	const response = await fetch(apiBaseUrl);
	const tasks = await response.json();
	taskList.innerHTML = '';
	tasks.forEach(task => {
		const li = document.createElement('li');
		const toggleButtonClass = task.completed ? 'completed' : 'in-progress';
		li.innerHTML = `
            <span><b>Title:</b>${task.title} <br><b>Description:</b>${task.description} <br><b>Date:</b> ${task.endDate}</span><br>
            <button class="toggle-button ${toggleButtonClass}" onclick="toggleTaskCompletion(${task.id}, ${task.completed})">
            	${task.completed ? 'Completed' : 'In Progress'}
        	</button>
            <button class="delete" onclick="deleteTask(${task.id})">Delete</button>`;
		taskList.appendChild(li);
	});
}

// Add a new task
taskForm.addEventListener('submit', async (event) => {
	event.preventDefault();
	const title = document.getElementById('taskTitle').value;
	const description = document.getElementById('taskDescription').value;
	const endDate = document.getElementById('taskDate').value;

	console.log(endDate);

	await fetch(apiBaseUrl, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify({ title, description, endDate }),
	});

	taskForm.reset();
	fetchTasks();
});

// Initial fetch of tasks
fetchTasks();

async function deleteTask(id) {
	const response = await fetch(`${apiBaseUrl}/${id}`, {
		method: 'DELETE'
	});
	if (response.ok) {
		fetchTasks(); // Refresh task list after deletion
	} else {
		alert('Failed to delete task.');
	}
}

async function toggleTaskCompletion(id, currentStatus) {
	const updatedStatus = !currentStatus;

	// Update the task completion status in the database
	await fetch(`${apiBaseUrl}/${id}`, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify({ completed: updatedStatus }),
	});

	fetchTasks(); // Refresh the task list
}