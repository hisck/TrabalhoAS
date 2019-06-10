import { NgModule } from '@angular/core';

import { UniticketSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [UniticketSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [UniticketSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class UniticketSharedCommonModule {}
