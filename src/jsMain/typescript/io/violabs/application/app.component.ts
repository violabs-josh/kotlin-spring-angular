import { Component } from '@angular/core'
import * as common_top from 'common'
import E = common_top.io.violabs.application.Example

interface Another {
  name: string
}

@Component({
  selector: 'app-root',
  // templateUrl: '../../../../app.component.html',
  template: `{{title}} {{type.name}} {{type.description}} {{hmm.name}}`,
  styleUrls: ['../../../../app.component.sass']
})
export class AppComponent {
  title = 'test-bun'
  type: E = {
    name: 'test',
    description: 'Here is an example of a common type from the common module.'
  }
  hmm: Another = {
    name: 'hmm'
  }

  constructor() {
    console.log(this.type.name, this.type.description)
  }
}
