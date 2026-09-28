

async function loadTasks() {
    const response = await fetch("/tasks");
    const tasks = await response.json();

    const list = document.getElementById("task-list");
    list.innerHTML = "";

    tasks.forEach(task => {
        const li = document.createElement("li");
        li.textContent = task.name;

        if (task.done) {
            li.classList.add("done");
        }

        const doneBtn = document.createElement("button");
        doneBtn.textContent = "Klar";
        doneBtn.addEventListener("click", async () => {
            const response = await fetch(`/tasks/${task.id}`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ done: true })
            });
            if (!response.ok) {
                const errorText = await response.text();
                document.getElementById("error-message").textContent = errorText;
                return;
            }
            document.getElementById("error-message").textContent = "";
            loadTasks();
        });
        li.appendChild(doneBtn);

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "Ta bort";
        deleteBtn.addEventListener("click", async () => {
            await fetch(`/tasks/${task.id}`, { method: "DELETE" });
            loadTasks();
        });
        li.appendChild(deleteBtn);

        list.appendChild(li);
    });
}

document.getElementById("add-task-form").addEventListener("submit", async (event) => {
    event.preventDefault();
    const input = document.getElementById("task-name-input");

    const response = await fetch("/tasks", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name: input.value, done: false })
    });

    if (!response.ok) {
        const errorText = await response.text();
        document.getElementById("error-message").textContent = errorText;
        return;
    }

    document.getElementById("error-message").textContent = "";
    input.value = "";
    loadTasks();
});

loadTasks();