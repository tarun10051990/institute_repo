import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth';
import { RegisterRequest } from '../../models/interfaces';

@Component({
  selector: 'app-register',
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  form: RegisterRequest = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    phone: '',
    educationLevel: 'CLASS_10_12',
    schoolName: '',
    city: '',
  };
  confirmPassword = '';
  error = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (this.form.password !== this.confirmPassword) {
      this.error = 'Passwords do not match';
      return;
    }
    this.error = '';
    this.loading = true;
    this.authService.register(this.form).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.loading = false;
        this.error = err.error?.error || 'Registration failed. Please try again.';
      },
    });
  }
}
