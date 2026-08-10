import { Component, Input } from '@angular/core';
import { CAREER_TEST_URL } from '../../../models/career-test';

@Component({
  selector: 'app-career-test-button',
  imports: [],
  templateUrl: './career-test-button.html',
  styleUrl: './career-test-button.scss',
})
export class CareerTestButton {
  @Input() label = 'Take Career Test';
  @Input() variant: 'primary' | 'yellow' | 'light' = 'primary';
  @Input() large = false;

  readonly url = CAREER_TEST_URL;
}
