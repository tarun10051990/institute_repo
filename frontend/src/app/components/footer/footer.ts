import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CAREER_TEST_URL } from '../../models/career-test';

@Component({
  selector: 'app-footer',
  imports: [RouterLink],
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
})
export class Footer {
  readonly careerTestUrl = CAREER_TEST_URL;
}
