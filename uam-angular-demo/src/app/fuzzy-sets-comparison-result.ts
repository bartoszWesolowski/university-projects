import {FuzzySet} from './fuzzy-set'

export class FuzzySetsComparisonResult {
    result: number;

    setAValuesUsed: Array<number>;

    setBValuesUsed: Array<number>;
    
    constructor(private responseObject: object) {
        this.result = responseObject['result'];
        this.setAValuesUsed = responseObject['setA'];
        this.setBValuesUsed = responseObject['setB'];
    }
}

export class FuzzySetsComparisonRequestBody {

    setA: Array<number>;
    setB: Array<number>;

    constructor(private setAWrapper: FuzzySet, private setBWrapper: FuzzySet, public config: object, public method: string) {
        this.setA = setAWrapper.getValues();
        this.setB = setBWrapper.getValues();
    }
}
