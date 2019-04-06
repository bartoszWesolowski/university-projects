import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import {FuzzySet} from './fuzzy-set'
import {FuzzySetsComparisonResult, FuzzySetsComparisonRequestBody} from './fuzzy-sets-comparison-result'

@Injectable()
export class CompareSetsService {

  constructor(private http: HttpClient) { }

  compareSets(requestBody: FuzzySetsComparisonRequestBody) {
    return this.http.post('http://bartek887.pythonanywhere.com/fuzzy/similarity', requestBody);
  } 
}
