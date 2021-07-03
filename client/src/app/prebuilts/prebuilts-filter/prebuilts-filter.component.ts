import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { Task } from 'src/app/shared/interfaces/task.interface';

/**
 * @author Filippo Casarosa
 */
@Component({
  selector: 'app-prebuilts-filter',
  templateUrl: './prebuilts-filter.component.html',
  styleUrls: ['./prebuilts-filter.component.scss']
})
export class PrebuiltsFilterComponent implements OnInit {
  task: Task = {
    name: 'Utilizzo',
    completed: true,
    color: 'primary',
    subtasks: [
      {name: 'Gaming', completed: true, color: 'primary'},
      {name: 'Lavoro', completed: true, color: 'accent'},
      {name: 'Casa', completed: true, color: 'warn'}
    ]};

  allComplete: boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

  updateAllComplete() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
  }

  someComplete(): boolean {
    if (this.task.subtasks == null) {
      return false;
    }
    return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.task.subtasks == null) {
      return;
    }
    this.task.subtasks.forEach(t => t.completed = completed);
  }
}
