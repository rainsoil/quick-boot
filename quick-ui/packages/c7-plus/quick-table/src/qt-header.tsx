import { has } from "lodash-es";
import { defineComponent, toRefs, renderSlot, inject } from "vue";

export default defineComponent({
    name: "Header",
    props: {
        title: String
    },
    setup(props, { slots }) {
        return {
            ...toRefs(props),
            slots
        }
    },
    render() {
        const { slots: { theader, headerLeft, headerRight }, slots, title } = this

        const renderHeader = () => {
            if (!title && !headerLeft && !headerRight) return null
            return <div class='quick-table-header'>
                <div class={{ 'qt-left-title': !!title }}>{headerLeft ? renderSlot(slots, 'headerLeft') : title}</div>
                <div>{headerRight ? renderSlot(slots, 'headerRight') : null}</div>
            </div>
        }

        return <>
            {has(slots, 'theader') ? renderSlot(slots, 'theader') : renderHeader()}
        </>
    }
})