import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  standalone: true,
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private dialogRef: MatDialogRef<MovieDetailsComponent>) {}

  closeDialog() {
    this.dialogRef.close();
  }
}
