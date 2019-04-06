import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import {CompareSetsService} from './compare-sets.service'
import { AppComponent } from './app.component';
import { FuzzySetComparatorComponent } from './fuzzy-set-comparator/fuzzy-set-comparator.component';

import { ChartsModule } from 'ng2-charts';

@NgModule({
  declarations: [
    AppComponent,
    FuzzySetComparatorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ChartsModule,
  ],
  providers: [
    CompareSetsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
