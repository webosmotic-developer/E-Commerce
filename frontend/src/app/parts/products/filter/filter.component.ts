import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SearchCriteria} from '../../../models/SearchCriteria';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {
  searchCriteria: SearchCriteria;
  @Output()
  public childEvent: EventEmitter<SearchCriteria> = new EventEmitter<SearchCriteria>();
  constructor(private event: EventEmitter<SearchCriteria>) { }
  ngOnInit(): void {
  }
  onSubmit() {
    this.searchCriteria.brand = 'Iball';
    this.childEvent.emit(this.searchCriteria);
  }
}
