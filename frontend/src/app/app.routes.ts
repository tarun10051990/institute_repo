import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { About } from './components/about/about';
import { Programs } from './components/programs/programs';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Dashboard } from './components/dashboard/dashboard';
import { Assessment } from './components/assessment/assessment';
import { TestTaking } from './components/test-taking/test-taking';
import { Results } from './components/results/results';
import { Contact } from './components/contact/contact';
import { MbtiTest } from './components/mbti-test/mbti-test';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'about', component: About },
  { path: 'programs', component: Programs },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'contact', component: Contact },
  { path: 'mbti', component: MbtiTest },
  { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
  { path: 'assessment', component: Assessment, canActivate: [authGuard] },
  {
    path: 'test/:sessionId/:categoryId',
    component: TestTaking,
    canActivate: [authGuard],
  },
  {
    path: 'results/:sessionId',
    component: Results,
    canActivate: [authGuard],
  },
  { path: '**', redirectTo: '' },
];
