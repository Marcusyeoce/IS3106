import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAttractionComponent } from './view-attraction.component';

describe('ViewAttractionComponent', () => {
  let component: ViewAttractionComponent;
  let fixture: ComponentFixture<ViewAttractionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAttractionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAttractionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
