import {range} from "lodash";

export class FuzzySet {

    splitter = /\s+|,|;/;

    static fromArray(values: Array<number>): FuzzySet {
        return new FuzzySet(values.join(' '));
    };
    
    constructor(public setAsString: string) {
        this.setAsString = setAsString;
    };

    getValues(): Array<number> {
        return this.setAsString.split(this.splitter)
        .map(parseFloat)
        .filter((value) => !isNaN(value));
    };

};

export class FuzzySetChart {

    labels: Array<number>;

    dataset: Array<any>;

    type: string;

    options: any;

    constructor(public setA: FuzzySet, public setB: FuzzySet) {
        this.dataset = [
            { 'data': this.setA.getValues(), label: 'Set A' },
            { 'data': this.setB.getValues(), label: 'Set B' }
        ];
        let sizeA = this.setA.getValues().length;
        let sizeB = this.setB.getValues().length;
        this.labels = range(Math.max(sizeA, 20));
        this.type = 'line';
        this.options = {
            responsive: true
          };
    }

    public setSets(setA: FuzzySet, setB: FuzzySet) : void {
        this.dataset = [
            { 'data': setA.getValues(), label: 'Set A' },
            { 'data': setB.getValues(), label: 'Set B' }
        ];;
        let sizeA = setA.getValues().length;
        let sizeB = setB.getValues().length;
        let tempLabels = range(Math.max(sizeA, sizeB));
        //TODO:Labels not updating for some reason...
        this.labels = JSON.parse(JSON.stringify(tempLabels));
    }

  public lineChartColors:Array<any> = [
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // dark grey
      backgroundColor: 'rgba(77,83,96,0.2)',
      borderColor: 'rgba(77,83,96,1)',
      pointBackgroundColor: 'rgba(77,83,96,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    },
    { // grey
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
}
