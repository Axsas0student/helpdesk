import { Component, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

type Category = {
  id: number;
  name: string;
  description: string;
};

type Ticket = {
  id: number;
  title: string;
  description: string;
  status: string;
  authorEmail: string;
  category: Category;
  createdAt: string;
  updatedAt: string;
};

@Component({
  selector: 'app-root',
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');

  private apiUrl = 'http://localhost:8080/api';

  loginUsername = '';
  loginPassword = '';

  registerUsername = '';
  registerPassword = '';
  registerEmail = '';
  registerRole = 'USER';

  token = localStorage.getItem('token') || '';
  loggedUsername = localStorage.getItem('username') || '';
  loggedRole = localStorage.getItem('role') || '';

  loginMessage = '';
  registerMessage = '';

  categories: Category[] = [];
  tickets: Ticket[] = [];

  categoryName = '';
  categoryDescription = '';

  ticketTitle = '';
  ticketDescription = '';
  ticketCategoryId: number | null = null;

  constructor(private http: HttpClient) {
    if (this.token) {
      this.loadCategories();

      this.loadTickets();
    }
  }

  private authHeaders() {
    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${this.token}`
      })
    };
  }

  register() {
    const body = {
      username: this.registerUsername,
      password: this.registerPassword,
      email: this.registerEmail,
      role: this.registerRole
    };

    this.http.post<any>(`${this.apiUrl}/auth/register`, body).subscribe({
      next: (response) => {
        this.saveAuth(response);
        this.registerMessage = 'Rejestracja zakończona sukcesem.';
        this.loginMessage = '';
        this.loadCategories();
        this.loadTickets();
      },
      error: (error) => {
        this.registerMessage = this.getErrorMessage(error);
      }
    });
  }

  login() {
    const body = {
      username: this.loginUsername,
      password: this.loginPassword
    };

    this.http.post<any>(`${this.apiUrl}/auth/login`, body).subscribe({
      next: (response) => {
        this.saveAuth(response);
        this.loginMessage = 'Logowanie zakończone sukcesem.';
        this.registerMessage = '';
        this.loadCategories();
        this.loadTickets();
      },
      error: (error) => {
        this.loginMessage = this.getErrorMessage(error);
      }
    });
  }

  logout() {
    this.token = '';
    this.loggedUsername = '';
    this.loggedRole = '';
    localStorage.clear();
    this.categories = [];
    this.tickets = [];
  }

  private saveAuth(response: any) {
    this.token = response.token;
    this.loggedUsername = response.username;
    this.loggedRole = response.role;

    localStorage.setItem('token', this.token);
    localStorage.setItem('username', this.loggedUsername);
    localStorage.setItem('role', this.loggedRole);
  }

  loadCategories() {
    this.http.get<Category[]>(`${this.apiUrl}/categories`, this.authHeaders()).subscribe({
      next: (response) => {
        this.categories = response;
      },
      error: () => {
        console.log('Nie udało się pobrać kategorii.');
      }
    });
  }

  createCategory() {
    const body = {
      name: this.categoryName,
      description: this.categoryDescription
    };

    this.http.post<Category>(`${this.apiUrl}/categories`, body, this.authHeaders()).subscribe({
      next: () => {
        this.categoryName = '';
        this.categoryDescription = '';
        this.loadCategories();
      },
      error: (error) => {
        alert(this.getErrorMessage(error));
      }
    });
  }

  loadTickets() {
    const endpoint = this.loggedRole === 'ADMIN'
      ? `${this.apiUrl}/tickets`
      : `${this.apiUrl}/tickets/my`;

    this.http.get<Ticket[]>(endpoint, this.authHeaders()).subscribe({
      next: (response) => {
        this.tickets = response;
      },
      error: () => {
        console.log('Nie udało się pobrać zgłoszeń.');
      }
    });
  }

  createTicket() {
    const body = {
      title: this.ticketTitle,
      description: this.ticketDescription,
      categoryId: this.ticketCategoryId
    };

    this.http.post<Ticket>(`${this.apiUrl}/tickets`, body, this.authHeaders()).subscribe({
      next: () => {
        this.ticketTitle = '';
        this.ticketDescription = '';
        this.ticketCategoryId = null;
        this.loadTickets();
      },
      error: (error) => {
        alert(this.getErrorMessage(error));
      }
    });
  }

  updateTicketStatus(ticket: Ticket, newStatus: string) {
    const body = {
      status: newStatus
    };

    this.http.patch<Ticket>(
      `${this.apiUrl}/tickets/${ticket.id}/status`,
      body,
      this.authHeaders()
    ).subscribe({
      next: () => {
        this.loadTickets();
      },
      error: (error) => {
        alert(error.error?.message || 'Nie udało się zmienić statusu zgłoszenia.');
      }
    });
  }

  private getErrorMessage(error: any): string {
    if (error.error?.errors) {
      return Object.values(error.error.errors).join('\n');
    }

    return error.error?.message || 'Wystąpił błąd.';
  }
}
