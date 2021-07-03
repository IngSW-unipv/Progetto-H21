import { ThemePalette } from "@angular/material/core";

/**
 * @author Filippo Casarosa
 */
export interface Task {
  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
}
