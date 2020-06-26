import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  recepieApi = 'https://api.edamam.com/search?q=';
  recepieAppid = '&app_id=759eaa74';
  recepieKey = '&app_key=3ddf6925658a9c517988e5e1d9d08424';
  placeApi = 'https://api.foursquare.com/v2/venues/explore/?near=';
  clientId = '&client_id=QFQXIJYLRGX4L0SRARBXMAAHR34QEUD0R4BP1NQJOJOIH5J3';
  clientSecret = '&client_secret=55RTPTPO1YZAWDYSGB4F02UPNHPXJJZM2BYHAZNX3BIYGMCQ';
  version = '&v=20200624';

  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      this._http.get(this.recepieApi + this.recipeValue + this.recepieAppid + this.recepieKey).subscribe((res: any) => {
        this.recipeList = Object.keys(res.hits).map(function (k) {
          const i = res.hits[k].recipe;
          return {name: i.label, icon: i.image, url: i.uri};
        })
      });
    }
    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      this._http.get(this.placeApi + this.placeValue+ '&limit=10&section=' + this.recipeValue + this.clientId + this.clientSecret + this.version).subscribe((place:any) => {
        this.venueList = Object.keys(place.response.groups[0].items).map(function (k) {
          const i = place.response.groups[0].items[k].venue;
          return {name: i.name, address: i.location.address+","+i.location.formattedAddress[2]+","+i.location.formattedAddress[3]};
        })
      });
    }
  }
}
