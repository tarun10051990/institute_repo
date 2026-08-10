import { Component, Input } from '@angular/core';
import { CAREER_TEST_LOGO, CAREER_TEST_LOGO_ALT } from '../../../models/career-test';

@Component({
  selector: 'app-career-today-logo',
  imports: [],
  templateUrl: './career-today-logo.html',
  styleUrl: './career-today-logo.scss',
})
export class CareerTodayLogo {
  @Input() size: 'sm' | 'md' | 'lg' = 'md';

  readonly src = CAREER_TEST_LOGO;
  readonly alt = CAREER_TEST_LOGO_ALT;
}
