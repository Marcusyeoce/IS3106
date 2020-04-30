import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchAttractionsComponent } from './search-attractions.component';

describe('SearchAttractionsComponent', () => {
  let component: SearchAttractionsComponent;
  let fixture: ComponentFixture<SearchAttractionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchAttractionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchAttractionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
