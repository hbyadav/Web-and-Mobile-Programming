A simple timer service for Angular, base on RxJS.
<!--more-->

Name/ID(string) base API. RxJS object not exposed.

### Install

```sh
npm install ng2-simple-timer
```

### Usage

__ng2-simple-timer__ is implemented as Angular injectable service name __SimpleTimer__.

#### Module

Add `SimpleTimer` into module providers 

```javascript
import { SimpleTimer } from 'ng2-simple-timer';

@NgModule({
  providers: [SimpleTimer]
})
```

#### Component

```javascript
import {SimpleTimer} from 'ng2-simple-timer';

export class ChildComponent {

  constructor(private st: SimpleTimer) { }

}
```

### API

#### newTimer

Create timer with optional delay start. Unit in second.

`newTimer(name: string, sec: number, delay: boolan = false): boolean`

`newTimer` will create timer `name` and tick every 'number' of seconds. Creating timer with the same name multiple times has no side effect.

`delay`: If set to true will delay the 1st tick till the end of the first interval.

Return `false` if timer `name` exist.

```javascript
this.st.newTimer('5sec', 5);
this.st.newTimer('5sec', 5, true);
```

#### newTimerCD

Create timer with optional customer delay. Unit in second.

`newTimerCD(name: string, sec: number, delay: number = 0): boolean`

`newTimerCD` will create timer `name` and tick every 'number' of seconds. Creating timer with the same name multiple times has no side effect.

`delay`: If set to X, will delay the 1st tick for X seconds.

Return `false` if timer `name` exist.

```javascript
this.st.newTimerCD('5sec', 5);
this.st.newTimerCD('5sec', 5, 10);
```

#### newTimerHR

Create High Resolution timer with optional customer delay. Unit in millisecond.

`newTimerHR(name: string, msec: number, delay: number = 0): boolean`

`newTimerHR` will create timer `name` and tick every 'number' of milliseconds. Creating timer with the same name multiple times has no side effect.

`delay`: If set to X, will delay the 1st tick for X milliseconds.

Return `false` if timer `name` exist.

```javascript
this.st.newTimerHR('5ms', 5);
this.st.newTimerHR('5ms', 5, 10);
```

#### delTimer

`delTimer(name: string): boolean`

`delTimer` will delete timer `name`

Return `false` if timer `name` does not exist.

```javascript
this.st.delTimer('5sec');
```

#### getTimer

`getTimer(): string[]`

`getTimer` will return all timer name in string array.

```javascript
let t: string[] = this.st.getTimer();
```

#### getSubscription

`getSubscription(): string[]`

`getSubscription` will return all subscription id in string array.

```javascript
let ids: string[] = this.st.getSubscription();
```

#### subscribe

`subscribe(name: string, callback: () => void): string`

`subscribe` will link `callback` function to timer `name`. Whenever timer `name` tick, `callback` will be invoked.

Return subscription id(string).

Return empty string if timer `name` does not exist.

Either use Lambda(fat arrow) in typescript to pass in callback or bind `this` to another variable in javascript, else `this` scope will be lost.

__Lambda(fat arrow)__

```javascript
counter: number = 0;
timerId: string;

ngOnInit() {
  // lazy mode
  this.timerId = this.st.subscribe('5sec', () => this.callback());
}

callback() {
  this.counter++;
}
```

#### stop

`unsubscribe(id: string): boolean`

`unsubscribe` will cancel subscription using `id`.

`unsubscribe` will return false if `id` is undefined or `id` is not found in subscription list.

```javascript
timerId: string;

this.st.unsubscribe(this.timerId);
```
