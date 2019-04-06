import { Component, OnInit } from '@angular/core';
import {FuzzySet, FuzzySetChart} from '../fuzzy-set'

import { CompareSetsService } from '../compare-sets.service';
import {FuzzySetsComparisonResult, FuzzySetsComparisonRequestBody} from '../fuzzy-sets-comparison-result'

@Component({
  selector: 'app-fuzzy-set-comparator',
  templateUrl: './fuzzy-set-comparator.component.html',
  styleUrls: ['./fuzzy-set-comparator.component.css']
})
export class FuzzySetComparatorComponent implements OnInit {

  setA: FuzzySet;

  setB: FuzzySet;

  config: Object;

  comparisonResult: FuzzySetsComparisonResult = null;

  chart: FuzzySetChart;

  chartData: Array<any> = [];
  constructor(public compareSetsService: CompareSetsService) {
    this.setA = new FuzzySet('0.8 0.85 0.9 0.95 1 0.8 0.85 0.9 0.95 1');
    this.setB = new FuzzySet('0.4 0.45 0.5 0.55 0.6 0.4 0.45 0.5 0.55 0.6');
    this.config = {
        method: 'minkowski',
        r: 2
    };

    this.chart = new FuzzySetChart(this.setA, this.setB);
    console.log(this.chart);
   }

  ngOnInit() {
  };

  onSubmit() {
    let requestBody = new FuzzySetsComparisonRequestBody(this.setA, this.setB, this.config, 'minkowski');
    let comparisonResult = this.compareSetsService.compareSets(requestBody);
    comparisonResult.subscribe(data => {
      this.comparisonResult = new FuzzySetsComparisonResult(data);
      this.setA = FuzzySet.fromArray(this.comparisonResult.setAValuesUsed);
      this.setB = FuzzySet.fromArray(this.comparisonResult.setBValuesUsed);
      this.chart.setSets(this.setA, this.setB);
    });
  };

  presentResult(): string {
    return JSON.stringify(this.comparisonResult || {});
  };

}
