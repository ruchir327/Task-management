// Create the TodoDto class
export class TaskDto {
    constructor(id, title, description, completed, assignedUser, assignedBy) {
      this.id = id;
      this.title = title;
      this.description = description;
      this.completed = completed;
      this.assignedUser = assignedUser;
      this.assignedBy = assignedBy;
    }
  }
  