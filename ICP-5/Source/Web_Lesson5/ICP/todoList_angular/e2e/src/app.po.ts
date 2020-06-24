import { browser, by, element } from 'protractor';

export class AppPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('.header h1')).getText();
  }

  /**
   * 添加一个 todo
   * @param {string} value 要输入的内容
   * @returns
   * @memberof AppPage
   */
  addOneTodo(value: string = 'new todo') {
    element(by.css('.header .add-todo')).sendKeys(value);
    element(by.css('.header .add-btn')).click();
    return browser.takeScreenshot();
  }

  /**
   * 添加指定 count 个 todo
   * @param {string} value 要输入的内容
   * @param {number} count 默认5个
   * @returns
   * @memberof AppPage
   */
  addMultipleTodo(value: string = 'new todo ', count: number = 5) {
    // 模拟添加5条数据
    for (let index = 0; index < count; index++) {
      // 模拟用户输入
      element(by.css('.header .add-todo')).sendKeys(value + index);
      element(by.css('.header .add-btn')).click();
    }
    return browser.takeScreenshot();
  }

}
